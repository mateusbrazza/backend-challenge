###### vpc/outputs.tf 
output "aws_public_subnet" {
  value = aws_subnet.public_jwt_node_group_subnet.*.id
}

output "vpc_id" {
  value = aws_vpc.jwt_node_group.id
}
