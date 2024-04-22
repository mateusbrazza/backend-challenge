provider "aws" {
  region = "us-east-1"
}

resource "aws_security_group" "ec2_sg" {
  name        = "grupo-seguranca-ec2"
  description = "Grupo de seguranca para instancias EC2 para permitir entrada HTTP e SSH e toda saida"

  ingress {
    description = "Permitir HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Permitir SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "Permitir todo o trafego de saida"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "Grupo de Seguranca Padrao EC2"
  }
}

resource "aws_key_pair" "chave_deployer" {
  key_name   = "chave-terraform"
  public_key =  file("~/.ssh/id_rsa.pub")
}

resource "aws_instance" "instancia_ec2" {
  ami                    = "ami-0c101f26f147fa7fd"
  instance_type          = "t2.nano"
  key_name               = aws_key_pair.chave_deployer.key_name
  user_data              = file("user_data.sh")
  vpc_security_group_ids = [aws_security_group.ec2_sg.id]
  tags = {
    Name = "Instância Implantada via Terraform"
  }
}

output "ip_publico" {
  value       = aws_instance.instancia_ec2.public_ip
  description = "O endereco IP publico da instancia EC2."
}