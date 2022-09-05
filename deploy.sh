sudo docker rm -f app
sudo docker pull randomquiz/rqs:latest
sudo docker run --name app -d -p 8080:8080 --network app randomquiz/rqs:latest
sudo docker image prune -f