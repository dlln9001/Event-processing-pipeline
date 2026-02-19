import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '50s', target: 100 }
    ]
};


export default function () {

    const res = http.post('http://localhost:8080/transaction', JSON.stringify({
        "type": "purchase",
        "account_id": 101,
        "merchant_id": 5005,
        "amount_cents": 4500,
        "currency": "USD"
        }));

    check(res, {
        'is status 201': (r) => r.status == 201,
    });
}