package transaction

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"

	"github.com/SidneyMoreira/bootCampsDIO/LabsPro/DesenvSistemPlanejFinanGolang/model/transaction"
	"github.com/SidneyMoreira/bootCampsDIO/LabsPro/DesenvSistemPlanejFinanGolang/util"
)

//GetTransactions bafgdgdsf
func GetTransactions(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}
	w.Header().Set("Content-type", "application/json")

	var transactions = transaction.Transactions{
		transaction.Transaction{
			Title:     "Salario",
			Amount:    4000.00,
			Type:      0,
			CreatedAt: util.StringToTime("2021-04-28 13:21:15"),
		},
	}

	_ = json.NewEncoder(w).Encode(transactions)
}

//CreateATransactions gsdfsdfsdf
func CreateATransactions(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		w.WriteHeader(http.StatusMethodNotAllowed)
		return
	}

	var res = transaction.Transactions{}
	var body, _ = ioutil.ReadAll(r.Body)

	_ = json.Unmarshal(body, &res)

	fmt.Print(res)

}
