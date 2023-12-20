resource "digitalocean_container_registry" "pocketurl-registry" {
  name                   = "pocketurl-registry"
  subscription_tier_slug = "starter"
}

variable "ssh_key" {}

resource "digitalocean_ssh_key" "pocketurl-ssh-key" {
  name       = "pocketurl-ssh-key"
  public_key = file(var.ssh_key)
}

resource "digitalocean_droplet" "pocketurl-droplet" {
  name   = "pocketurl-droplet"
  image  = "docker-18-04"
  region = "lon1"
  size   = "s-1vcpu-1gb"
  ssh_keys = [digitalocean_ssh_key.pocketurl-ssh-key.fingerprint]
}

output "droplet_ip" {
  value = digitalocean_droplet.pocketurl-droplet.ipv4_address
}
