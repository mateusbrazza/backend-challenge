output "endpoint" {
  value = aws_eks_cluster.jwt_node_group.endpoint
}

output "kubeconfig-certificate-authority-data" {
  value = aws_eks_cluster.jwt_node_group.certificate_authority[0].data
}
output "cluster_id" {
  value = aws_eks_cluster.jwt_node_group.id
}
output "cluster_endpoint" {
  value = aws_eks_cluster.jwt_node_group.endpoint
}
output "cluster_name" {
  value = aws_eks_cluster.jwt_node_group.name
}
