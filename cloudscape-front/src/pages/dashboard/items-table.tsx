import { useEffect, useState } from "react";
import {
  Box,
  SpaceBetween,
  TableProps,
  Header,
  Table,
} from "@cloudscape-design/components";
import RouterButton from "../../components/wrappers/router-button.tsx";
import RouterLink from "../../components/wrappers/router-link.tsx";
import { TextHelper } from "../../common/helpers/text-helper.ts";
import { SecurityGroup } from "@aws-sdk/client-ec2";

const SecurityColumnDefinitions: TableProps.ColumnDefinition<SecurityGroup>[] = [
  {
    id: "GroupId",
    header: "GroupId",
    sortingField: "GroupId",
    cell: (securityGroup) => (
      <RouterLink href={`/section1/items/${securityGroup.GroupId}`}>
        {securityGroup.GroupId}
      </RouterLink>
    ),
    isRowHeader: true,
  },
];

export default function SecurityTable() {
  const [loading, setLoading] = useState(true);
  const [securityGroup, setSecurityGroup] = useState<SecurityGroup[]>([]);
  const [selectedSecurityGroup, setSelectedSecurityGroup] = useState<SecurityGroup[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const apiClient = await fetch('/api/ec2');
        if (!apiClient.ok) {
          throw new Error('서버에서 응답을 받는 데 실패했습니다.');
        }

        const response = await apiClient.json();
        setSecurityGroup(response);
        setLoading(false);
      } catch (error) {
        console.error("보안 그룹 정보를 불러오는 중 오류 발생:", error);
      }
    })();
  }, []);

  return (
    <Table
      loading={loading}
      loadingText="Loading Items"
      selectionType="single"
      empty={
        <Box margin={{ vertical: "xs" }} textAlign="center" color="inherit">
          <SpaceBetween size="xxs">
            <div>
              <b>No Items</b>
              <Box variant="p" color="inherit">
                Item is a thing that is used to do something.
              </Box>
            </div>
            <RouterButton href="/section1/add">Add Item</RouterButton>
          </SpaceBetween>
        </Box>
      }
      columnDefinitions={SecurityColumnDefinitions}
      items={securityGroup}
      selectedItems={selectedSecurityGroup}
      onSelectionChange={(event: {
        detail: TableProps.SelectionChangeDetail<SecurityGroup>;
      }) => setSelectedSecurityGroup(event.detail.selectedItems)}
      header={
        <Header
          counter={
            !loading
              ? TextHelper.getHeaderCounterText(securityGroup, selectedSecurityGroup)
              : undefined
          }
          actions={
            <SpaceBetween direction="horizontal" size="xs">
              <RouterButton
                disabled={selectedSecurityGroup.length !== 1}
                href={`/section1/items/${
                    selectedSecurityGroup.length > 0 ? selectedSecurityGroup[0].GroupId : ""
                }`}
              >
                View
              </RouterButton>
              <RouterButton href="/section1/add">Add Item</RouterButton>
            </SpaceBetween>
          }
        >
          Items
        </Header>
      }
      footer={
        <Box textAlign="center">
          <RouterLink href="/section1">View all Items</RouterLink>
        </Box>
      }
    />
  );
}
