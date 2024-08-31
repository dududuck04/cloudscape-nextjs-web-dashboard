import type { NextApiResponse } from 'next';
import {EC2Service} from "../../../lib/ec2_service.ts"

import { SecurityGroup } from "@aws-sdk/client-ec2";


const { AWS_REGION, AWS_ACCESS_KEY, AWS_SECRET_KEY } = process.env;

export default async function ec2(res: NextApiResponse) {

    if (!AWS_REGION || !AWS_ACCESS_KEY || !AWS_SECRET_KEY) {
        return res.status(500).json({ error: '환경 변수가 설정되지 않았습니다.' });
    }

    const ec2Service = new EC2Service(AWS_REGION, AWS_ACCESS_KEY, AWS_SECRET_KEY);

    try {
        const securityGroups: SecurityGroup[] = await ec2Service.describeMain();
        res.status(200).json(securityGroups);

    } catch (error) {

        console.error("EC2 보안 그룹 정보 조회 중 오류 발생:", error);
        res.status(500).json({ error: '서버에서 데이터를 조회하는 중 오류가 발생했습니다.' });
    }
}
