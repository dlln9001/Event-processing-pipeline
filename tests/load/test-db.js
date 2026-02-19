import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '2s', target: 1 },
        { duration: '2s', target: 1 },
    ]
};


export default function () {

    const res = http.post('http://localhost:8080/test-db');

    check(res, {
        'is status 201': (r) => r.status == 201,
    });

    sleep(1);
}