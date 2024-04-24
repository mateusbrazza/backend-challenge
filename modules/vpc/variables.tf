####### modules/vpc/variables.tf

variable "vpc_cidr" {
  default = "172.31.0.0/16"  // Substitua pelo ID da sua VPC na AWS
}
variable "access_ip" {
  description = "IP address that can access the resources"
  type        = string
  default     = "0.0.0.0/0"  // Por padrão, permite acesso de qualquer lugar
}

variable "public_sn_count" {
  description = "Number of public subnets to create"
  type        = number
  default     = 1
}

variable "public_cidrs" {
  description = "List of CIDR blocks for public subnets"
  type        = list(string)
  default     = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
}

variable "instance_tenancy" {
  description = "Tenancy of the EC2 instances"
  type        = string
  default     = "default"
}

variable "tags" {
  description = "Tags to be applied to all resources"
  type        = map(string)
  default     = {
    Environment = "development"
    Project     = "My Project"
  }
}

variable "map_public_ip_on_launch" {
  description = "Whether to assign a public IP on EC2 instances at launch"
  type        = bool
  default     = true
}

variable "rt_route_cidr_block" {
  description = "CIDR block for the default route in the route table"
  type        = string
  default     = "0.0.0.0/0"
}
