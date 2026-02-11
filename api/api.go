package api

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func Run() {
	fmt.Println("api.Run() running")
	r := gin.Default()

	r.GET("/ping", func(ctx *gin.Context) {
		ctx.JSON(http.StatusOK, "hello")
	})

	r.Run()
}
