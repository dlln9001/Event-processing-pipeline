package api

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"time"
	"os"

	"github.com/gin-gonic/gin"
	"github.com/segmentio/kafka-go"

	"event_processing_pipeline/models"
)

func Run() {
	fmt.Println("api.Run() running")

	kafkaHost := os.Getenv("KAFKA_HOST")

	w := &kafka.Writer{
		Addr:         kafka.TCP(fmt.Sprintf("%s:9092", kafkaHost)),
		Topic:        "topic-A",
		Balancer:     &kafka.LeastBytes{},
		Async:        false,
		BatchTimeout: 8 * time.Millisecond,
		WriteTimeout: 5 * time.Second,
	}

	defer w.Close()

	r := gin.Default()

	r.GET("/ping", func(ctx *gin.Context) {
		ctx.JSON(http.StatusOK, "hello")
	})

	// makes a transaction object and sends a transaction event to kafka
	r.POST("/transaction", func(ctx *gin.Context) {
		var req models.Transaction
		err := ctx.ShouldBindJSON(&req)

		if err != nil {
			ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
			return
		}

		payload, err := json.Marshal(req)

		if err != nil {
			ctx.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to encode JSON"})
			return
		}

		err = w.WriteMessages(context.Background(),
			kafka.Message{
				Key:   []byte("Key-A"),
				Value: []byte(payload),
			},
		)

		if err != nil {
			log.Println("failed to write messages:", err)
			ctx.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to send to Kafka"})
			return
		}

		// Return the result to the user
		ctx.JSON(http.StatusCreated, "event sent")

	})

	r.Run(":8081")
}
