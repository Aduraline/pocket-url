resource "digitalocean_container_registry" "pocketurl" {
  name                   = "pocketurl"
  subscription_tier_slug = "starter"
}

resource "digitalocean_droplet" "pocketurl" {
  name   = "pocketurl"
  image  = "docker-18-04"
  region = "lon1"
  size   = "s-1vcpu-1gb"
}

output "droplet_ip" {
  value = digitalocean_droplet.pocketurl.ipv4_address
}
