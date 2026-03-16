terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }

  }

  backend "s3" {
    bucket       = "event-processing-pipeline"
    key          = "terraform/terraform.tfstate"
    region       = "us-east-1"
    use_lockfile = true
    profile      = "terraform-local"
  }

}

provider "aws" {
  region = "us-east-1"
  profile = "terraform-local"
}

# Automatically find the latest Amazon Linux AMI ID
data "aws_ami" "latest_amazon_linux" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-2023*x86_64"]
  }
}

# Create the EC2 Instance
resource "aws_instance" "my_first_server" {
  ami           = data.aws_ami.latest_amazon_linux.id
  instance_type = "t3.small"

  tags = {
    Name = "MyTerraformInstance"
  }
}
