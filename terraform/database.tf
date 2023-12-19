resource "digitalocean_database_cluster" "pocketurl-db" {
  name       = "pocketurl-postgres-cluster"
  engine     = "pg"
  version    = "15"
  size       = "db-s-1vcpu-1gb"
  region     = "lon1"
  node_count = 1
}
