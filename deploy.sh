sudo docker rm -f app
sudo docker pull quizbox/app:latest
sudo docker run --name app -d -p 8080:8080 --network app quizbox/app:latest
sudo docker image prune -f