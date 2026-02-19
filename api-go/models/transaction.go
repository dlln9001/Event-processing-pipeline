package models

type Transaction struct {
	Type             string `json:"type" binding:"required"`
	AccountID        int    `json:"account_id" binding:"required"`
	MerchantID       *int   `json:"merchant_id"`        // Optional: can be null
	ReferenceEventID *int   `json:"reference_event_id"` // Optional: can be null
	AmountCents      int    `json:"amount_cents" binding:"required"`
	Currency         string `json:"currency" binding:"required,len=3"` // Validates exactly 3 chars
}