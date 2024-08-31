import {EC2Client, DescribeSecurityGroupsCommand, SecurityGroup, EC2ClientConfig, Filter} from "@aws-sdk/client-ec2";

export class EC2Service {
    private ec2_service: EC2Client;

    constructor(region: string, accessKeyId: string, secretAccessKey: string) {
        const config: EC2ClientConfig = {
            region,
            credentials: { accessKeyId, secretAccessKey },
        };
        this.ec2_service = new EC2Client(config);
    }

    async describeSecurityGroups(filters?: Filter[]): Promise<SecurityGroup[]> {
        const command = new DescribeSecurityGroupsCommand({ Filters: filters });
        try {
            const { SecurityGroups: response } = await this.ec2_service.send(command);
            return response || [];

        } catch (error) {
            console.error("Error listing EC2 security groups:", error);
            throw error;
        }
    }


    // async describeMain(): Promise<string[]> {
    //     const result: string[] = [];
    //
    //     const filters: Filter[] = [
    //         { Name: "vpc-id", Values: ["vpc-06ce6927c8680af58"] },
    //         // { Name: "group-name", Values: ["my-security-group"] },
    //     ];
    //
    //     try {
    //         const securityGroups = await this.describeSecurityGroups(filters);
    //
    //         securityGroups.map(group => {
    //
    //             const groupName = group.GroupName || 'No Group Name';
    //             const groupId = group.GroupId || 'No Group ID';
    //             const description = group.Description || 'No Description';
    //             const vpcId = group.VpcId || 'No VPC ID';
    //
    //             const tagsString = group.Tags?.map(tag => `${tag.Key}: ${tag.Value}`).join(", ") || 'No Tags';
    //
    //             result.push(groupName,groupId,description,vpcId,tagsString)
    //
    //         });
    //
    //         console.log(result);
    //
    //         return result;
    //
    //     } catch (error) {
    //         console.error("Error processing security groups:", error);
    //         return [];
    //     }
    //
    // }
    async describeMain(): Promise<SecurityGroup[]> {
        const filters: Filter[] = [
            {Name: "vpc-id", Values: ["vpc-0cc0e3a28a8e64df3"]},
        ];

        try {
            const securityGroups = await this.describeSecurityGroups(filters);

            return securityGroups.map(group => ({
                GroupId: group.GroupId ?? 'No Group ID',
                GroupName: group.GroupName ?? 'No Group Name',
                Description: group.Description ?? 'No Description',
                Tags: group.Tags ?? [],
                VpcId: group.VpcId ?? 'No VPC ID',
            }));
        } catch (error) {
            console.error("Error processing security groups:", error);
            return [];
        }
    }
}