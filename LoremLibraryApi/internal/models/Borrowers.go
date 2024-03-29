package models

import "gorm.io/gorm"

type Borrowers struct {
	gorm.Model
	Google_ID    string `gorm:"type:varchar(255)" json:"google_id" form:"google_id"`
	Email        string `gorm:"type:varchar(255);not null" json:"email" form:"email"`
	Name         string `gorm:"type:varchar(50);not null" json:"name" form:"name"`
	Phone_Number string `gorm:"type:varchar(35)" json:"phone_number" form:"phone_number"`
	Address      string `gorm:"type:varchar(50)" json:"address" form:"address"`
	Avatar       string `gorm:"type:varchar(255)" json:"avatar" form:"avatar"`
	Active       string `gorm:"type:varchar(10);default:false" json:"active" form:"active"`
	Collection   []Collection
	Lending      []Lending
	Reviews      []Reviews
}
