FROM ubuntu:latest
LABEL authors="johndiddles"

ENTRYPOINT ["top", "-b"]