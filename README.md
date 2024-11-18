# ToolsChallenge
Tools Java Challenge [C] 

Instruções para testes
via postman
POST
cria o pagamento
http://localhost:8080/api/pagamentos
body->raw
 {
        "transacao": {
            "cartao": "4444********1234",
                "descricao": {
                "valor": 1600.5,
                "dataHora": "14/11/2024 20:13:21",
                "estabelecimento":"PetShop Mundo cão"
               
            },
            "formaPagamento": {
                "tipo": "AVISTA",
                "parcelas": 1
            }
        }
    }

    Pretty
    {
    "transacao": {
        "cartao": "4444********1234",
        "id": "050dda56-d988-4b8d-bfb4-198e3f58240a",
        "descricao": {
            "valor": 1600.5,
            "dataHora": "18/11/2024 12:54:46",
            "estabelecimento": "PetShop Mundo cão",
            "nsu": "1859589675403995648",
            "codigoAutorizacao": "912869607",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": 1
        }
    }
}

Consulta pagamento
GET
http://localhost:8080/api/pagamentos/050dda56-d988-4b8d-bfb4-198e3f58240a


{
    "transacao": {
        "cartao": "4444********1234",
        "id": "050dda56-d988-4b8d-bfb4-198e3f58240a",
        "descricao": {
            "valor": 1600.5,
            "dataHora": "18/11/2024 12:54:46",
            "estabelecimento": "PetShop Mundo cão",
            "nsu": "1859589675403995648",
            "codigoAutorizacao": "912869607",
            "status": "AUTORIZADO"
        },
        "formaPagamento": {
            "tipo": "AVISTA",
            "parcelas": 1
        }
    }
}

Consuta todos os pagamentos

http://localhost:8080/api/pagamentos

[
    {
        "transacao": {
            "cartao": "4444********1234",
            "id": "050dda56-d988-4b8d-bfb4-198e3f58240a",
            "descricao": {
                "valor": 1600.5,
                "dataHora": "18/11/2024 12:54:46",
                "estabelecimento": "PetShop Mundo cão",
                "nsu": "1859589675403995648",
                "codigoAutorizacao": "912869607",
                "status": "AUTORIZADO"
            },
            "formaPagamento": {
                "tipo": "AVISTA",
                "parcelas": 1
            }
        }
    },
    {
        "transacao": {
            "cartao": "4444********1234",
            "id": "f8560c56-f6ef-4ddd-b844-2804b1d57d57",
            "descricao": {
                "valor": 7600.5,
                "dataHora": "18/11/2024 12:57:12",
                "estabelecimento": "Casa Cruz",
                "nsu": "-1553486168703994880",
                "codigoAutorizacao": "599656804",
                "status": "AUTORIZADO"
            },
            "formaPagamento": {
                "tipo": "PARCELADO_LOJA",
                "parcelas": 1
            }
        }
    }
]


Estorno (Pesquisar pelo Id Criado no pagamentto)
http://localhost:8080/api/pagamentos/f8560c56-f6ef-4ddd-b844-2804b1d57d57/estorno

{
    "transacao": {
        "cartao": "4444********1234",
        "id": "f8560c56-f6ef-4ddd-b844-2804b1d57d57",
        "descricao": {
            "valor": 7600.5,
            "dataHora": "18/11/2024 12:57:12",
            "estabelecimento": "Casa Cruz",
            "nsu": "-1553486168703994880",
            "codigoAutorizacao": "599656804",
            "status": "CANCELADO"
        },
        "formaPagamento": {
            "tipo": "PARCELADO_LOJA",
            "parcelas": 1
        }
    }
}



