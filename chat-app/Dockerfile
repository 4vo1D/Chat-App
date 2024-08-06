FROM ubuntu:latest

RUN apt-get update

COPY index.html /var/www/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]