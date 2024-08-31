import { ItemsApiClient } from "./items-api-client";
import {EC2Service} from "./ec2_service"

const { AWS_REGION, AWS_ACCESS_KEY, AWS_SECRET_KEY } = process.env;

if (!AWS_REGION || !AWS_ACCESS_KEY || !AWS_SECRET_KEY) {
  throw new Error('환경 변수가 정의되지 않았습니다.');
}

export class ApiClient {
  private _itemsClient: ItemsApiClient | undefined;

  public get items() {
    if (!this._itemsClient) {
      this._itemsClient = new ItemsApiClient();
    }

    return this._itemsClient;
  }

  private _ec2Service: EC2Service | undefined;

  public get ec2Service() {
    if (!this._ec2Service) {

      this._ec2Service = new EC2Service(AWS_REGION as string, AWS_ACCESS_KEY as string, AWS_SECRET_KEY as string);
    }
    return this._ec2Service;
  }


}
