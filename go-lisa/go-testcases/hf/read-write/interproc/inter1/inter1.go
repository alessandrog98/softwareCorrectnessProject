package main

import (
	"bytes"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
)

// Define the Smart Contract structure
type SmartContract struct {
}


func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
	return shim.Success(nil)
}

func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {


  err := APIstub.PutState("samekey", []byte("myvalue"));

  s.Read() // UNSAFE
}

func (s *SmartContract) Read() {

	value, err := APIstub.GetState("samekey")

  if err != nil {
	 return shim.Error("Error")
  } else {
    return shim.Success(nil)
  }

}
