variable "aws_public_subnet" {
  default = "subnet-0781183002b13f8ef"  // Substitua pelo ID da sua sub-rede pública na AWS
}

variable "vpc_id" {
  default = "vpc-0cd17584692583def"  // Substitua pelo ID da sua VPC na AWS
}

variable "cluster_name" {
  default = "jwt-cluster"  // Nome do seu cluster EKS
}

variable "endpoint_private_access" {
  default = false  // Se o acesso ao endpoint é privado (true/false)
}

variable "endpoint_public_access" {
  default = true  // Se o acesso ao endpoint é público (true/false)
}

variable "public_access_cidrs" {
  default = ["0.0.0.0/0"]  // Lista de CIDRs permitidos para acesso público
}

variable "node_group_name" {
  default = "jwt-node-group"  // Nome do seu grupo de nós no cluster EKS
}

variable "scaling_desired_size" {
  default = 2  // Número desejado de instâncias no grupo de nós
}

variable "scaling_max_size" {
  default = 5  // Número máximo de instâncias no grupo de nós
}

variable "scaling_min_size" {
  default = 1  // Número mínimo de instâncias no grupo de nós
}

variable "instance_types" {
  default = ["t3.medium"]  // Lista de tipos de instância para o grupo de nós
}

variable "key_pair" {
  default = "chave-terraform"  // Caminho absoluto da chave pública SSH
}


variable "kubernetes_master" {
  description = "Endereço do cluster Kubernetes"
  type        = string
  default = "https://jwt-cluster-1f1b7b7b.hcp.us-east-1.eks.amazonaws.com"  // Endpoint do cluster EKS

}