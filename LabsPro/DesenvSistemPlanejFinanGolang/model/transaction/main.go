package transaction

import "time"

//Transaction dgdgdsfdsfs
type Transaction struct {
	Title     string    `json:"title"`
	Amount    float32   `json:"amount"`
	Type      int       `json:"type"`
	CreatedAt time.Time `json:"created_at"`
}

//Transactions dfsafsdfsdfas
type Transactions []Transaction
