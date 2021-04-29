package util

import "testing"

//TestStringToTime ba;lbald;l
func TestStringToTime(t *testing.T) {
	var convertedtime = StringToTime("2021-04-28 13:21:15")

	if convertedtime.Year() != 2021 {
		t.Errorf("Espera que o ano seja 2021")
	}

	if convertedtime.Month() != 04 {
		t.Errorf("Espera que o mes seja 04")
	}

	if convertedtime.Hour() != 13 {
		t.Errorf("Espera que a hora seja 13")
	}
}
