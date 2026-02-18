import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '4s', target: 80 },
        { duration: '4s', target: 500 },
        { duration: '4s', target: 1000 },
    ]
};


export default function () {

    const res = http.post('http://localhost:8080/test-db');

    check(res, {
        'is status 200': (r) => r.status == 200,
    });

    sleep(1);
}