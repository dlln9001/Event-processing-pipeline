package api

import (
	"database/sql"
	"fmt"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"
	_ "github.com/jackc/pgx/v5/stdlib"
	"github.com/joho/godotenv"

	"event_processing_pipeline/models"
)

func Run() {
	fmt.Println("api.Run() running")

	godotenv.Load("../.env")
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

	r.POST("/transaction", func(ctx *gin.Context) {
		var req models.Transaction
		err := ctx.ShouldBindJSON(&req)
		
		if err != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}
		
		// 2. Prepare the SQL Statement
		// We use $1, $2, etc., for PostgreSQL (parameterized queries) to prevent SQL injection.
		query := `
			INSERT INTO transactions (type, account_id, merchant_id, reference_event_id, amount_cents, currency)
			VALUES ($1, $2, $3, $4, $5, $6)
			RETURNING event_id, timestamp;`

		// 3. Execute the query
		var newID int
		var createdAt time.Time

		// QueryRow is used because we expect exactly one row back (from RETURNING)
		err = db.QueryRow(
			query,
			req.Type,
			req.AccountID,
			req.MerchantID,       // Because this is a pointer (*int), it handles NULL automatically
			req.ReferenceEventID, // Same here
			req.AmountCents,
			req.Currency,
		).Scan(&newID, &createdAt) // Scan the RETURNING values into our variables

		if err != nil {
			// Log the error and tell the user something went wrong
			fmt.Println("Database Error:", err)
			ctx.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to save transaction"})
			return
		}

		// 4. Return the result to the user
		ctx.JSON(http.StatusCreated, gin.H{
			"message":    "Transaction created",
			"event_id":   newID,
			"created_at": createdAt,
		})

	})

	r.Run()
}
