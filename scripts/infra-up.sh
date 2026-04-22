#!/bin/bash
set -e # Exit immediately if a command fails

# Move to the terraform directory relative to the script location
cd "$(dirname "$0")/../terraform"

# --- 1. Infrastructure Provisioning ---
echo "🚀 Starting Terraform Apply..."
terraform init
terraform apply -auto-approve

# --- 2. Kubernetes Context Setup ---
echo "🔗 Connecting to EKS Cluster..."
# We extract the cluster name and region from Terraform outputs to avoid hardcoding
CLUSTER_NAME=$(terraform output -raw eks_cluster_name)
REGION=$(terraform output -raw region)

aws eks update-kubeconfig --region $REGION --name $CLUSTER_NAME

# --- 3. Argo CD Installation ---
echo "🛠️ Installing Argo CD..."
kubectl create namespace argocd || true
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

# Wait for Argo CD server to be ready
echo "⏳ Waiting for Argo CD to start..."
kubectl wait --for=condition=available --timeout=300s deployment/argocd-server -n argocd

# --- 4. Deploying the GitOps Application ---
echo "📦 Creating the Argo CD Application..."
# This points Argo CD to your MANIFEST repository
kubectl apply -f ./argocd/application.yaml

echo "✅ Environment is UP! Access your API via the LoadBalancer URL."
