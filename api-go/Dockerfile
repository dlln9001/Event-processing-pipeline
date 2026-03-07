FROM golang:1.26-alpine

WORKDIR /app

# add these files first so docker can cache all these libraries. So you don't need to redownload everything when you change the code.
COPY go.mod go.sum ./

RUN go mod download

COPY . .

ENTRYPOINT ["go", "run", "."]