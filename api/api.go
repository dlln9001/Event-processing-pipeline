package api

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"

	"github.com/gin-gonic/gin"
	_ "github.com/jackc/pgx/v5/stdlib"
	"github.com/joho/godotenv"
)

func Run() {
	fmt.Println("api.Run() running")
	godotenv.Load()
	// password := os.Getenv("DB_PASSWORD")
	db, err := sql.Open("pgx", "postgres://postgres:mypassword@localhost:5432/event_processing_db")

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
