package api

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
	_ "github.com/jackc/pgx/v5/stdlib"
	"github.com/joho/godotenv"
)

func Run() {
	fmt.Println("api.Run() running")

	godotenv.Load()
	dbUser := os.Getenv("DB_USER")
	dbPassword := os.Getenv("DB_PASSWORD")
	dbHost := os.Getenv("DB_HOST")
	dbPort := os.Getenv("DB_PORT")
	dbName := os.Getenv("DB_NAME")
	db, err := sql.Open("pgx", fmt.Sprintf("postgres://%s:%s@%s:%s/%s", dbUser, dbPassword, dbHost, dbPort, dbName))

	if err != nil {
		log.Fatal(err)
	}

	r := gin.Default()

	r.GET("/ping", func(ctx *gin.Context) {
		ctx.JSON(http.StatusOK, "hello")
	})

	r.POST("/test-db", func(ctx *gin.Context) {
		name := "test"
		age := 30

		query := "INSERT into test (name, age) VALUES ($1, $2)"
		_, err := db.Exec(query, name, age)

		if err != nil {
			panic(err)
		}

		ctx.JSON(http.StatusOK, "added to db")
	})

	r.Run()
}