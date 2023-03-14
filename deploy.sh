sudo docker rm -f app
sudo docker rmi quizbox/app
sudo docker run --name app -d -p 8080:8080 --network quizbox quizbox/app:latest
sudo docker image prune -f