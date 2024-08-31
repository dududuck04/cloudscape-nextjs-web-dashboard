import {
  TableProps,
  Table,
  PropertyFilter,
  Pagination,
  Box,
  SpaceBetween,
  Button,
} from "@cloudscape-design/components";
import {
  useCollection,
  PropertyFilterProperty,
  PropertyFilterOperator,
} from "@cloudscape-design/collection-hooks";
import { useEffect, useState } from "react";
import { TextHelper } from "../../../common/helpers/text-helper.ts";
import { PropertyFilterI18nStrings } from "../../../common/i18n/property-filter-i18n-strings.ts";
import { SecurityGroup } from "@aws-sdk/client-ec2";

const SecurityColumnDefinitions: TableProps.ColumnDefinition<SecurityGroup>[] = [
  {
    id: "GroupId",
    header: "GroupId",
    sortingField: "GroupId",
    cell: securityGroup => securityGroup.GroupId,
  },
  {
    id: "GroupName",
    header: "GroupName",
    sortingField: "GroupName",
    cell: securityGroup => securityGroup.GroupName,
    isRowHeader: true,
  },
  {
    id: "Description",
    header: "Description",
    sortingField: "Description",
    cell: securityGroup => securityGroup.Description,
    minWidth: 120,
  },
  {
    id: "VpcId",
    header: "VpcId",
    sortingField: "VpcId",
    cell: securityGroup => securityGroup.VpcId,
  },
  // {
  //   id: "Tags",
  //   header: "Tags",
  //   sortingField: "Tags",
  //   cell: securityGroup => (
  //       securityGroup.Tags.map(tag => `${tag.Key}: ${tag.Value}`).join(", ")
  //   ),
  // }
];

const SecurityColumnFilteringProperties: PropertyFilterProperty[] = [
  {
    propertyLabel: "GroupName",
    key: "GroupName",
    groupValuesLabel: "GroupName values",
    operators: [":", "!:", "=", "!="] as PropertyFilterOperator[],
  },
  {
    propertyLabel: "GroupId",
    key: "GroupId",
    groupValuesLabel: "GroupId values",
    operators: [":", "!:", "=", "!="] as PropertyFilterOperator[],
  },
  {
    propertyLabel: "Description",
    key: "Description",
    groupValuesLabel: "Description values",
    operators: [":", "!:", "=", "!="] as PropertyFilterOperator[],
  }
].sort((a, b) => a.propertyLabel.localeCompare(b.propertyLabel));

export default function SecurityGroupsTable() {
  const [loading, setLoading] = useState(true);
  const [securityGroup, setSecurityGroup] = useState<SecurityGroup[]>([]);
  const {
    items,
    actions,
    filteredItemsCount,
    collectionProps,
    paginationProps,
    propertyFilterProps,
  } = useCollection(securityGroup, {
    propertyFiltering: {
      filteringProperties: SecurityColumnFilteringProperties,
      empty: (
        <Box margin={{ vertical: "xs" }} textAlign="center" color="inherit">
          <div>
            <b>No Security Groups</b>
            <Box variant="p" color="inherit">
              No Security Groups found. Try adjusting your filter.
            </Box>
          </div>
        </Box>
      ),
      noMatch: (
        <Box margin={{ vertical: "xs" }} textAlign="center" color="inherit">
          <SpaceBetween size="xxs">
            <div>
              <b>No matches</b>
              <Box variant="p" color="inherit">
                We can't find a match.
              </Box>
            </div>
            <Button
              onClick={() =>
                actions.setPropertyFiltering({ tokens: [], operation: "and" })
              }
            >
              Clear filter
            </Button>
          </SpaceBetween>
        </Box>
      ),
    },
    pagination: { pageSize: 5 },
    sorting: {
      defaultState: {
        sortingColumn: SecurityColumnDefinitions[0],
        isDescending: true,
      },
    },
    selection: {},
  });

  // useEffect(() => {
  //   (async () => {
  //     const apiClient = new ApiClient();
  //     const response = await apiClient.ec2Service.describeMain();
  //     setSecurityGroup(response);
  //     // const items = await apiClient.items.getItems();
  //     // setData(items);
  //     setLoading(false);
  //   })();
  // }, []);

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
      {...collectionProps}
      items={items}
      columnDefinitions={SecurityColumnDefinitions}
      selectionType="single"
      variant="full-page"
      stickyHeader={true}
      resizableColumns={true}
      // header={
      //   <AllItemsPageHeader
      //     selectedItems={collectionProps.selectedItems ?? []}
      //     counter={
      //       loading
      //         ? undefined
      //         : TextHelper.getHeaderCounterText(
      //             securityGroup,
      //             collectionProps.selectedItems
      //           )
      //     }
      //   />
      // }
      loading={loading}
      loadingText="Loading Items"
      filter={
        <PropertyFilter
          {...propertyFilterProps}
          i18nStrings={PropertyFilterI18nStrings}
          filteringPlaceholder={"Filter Items"}
          countText={TextHelper.getTextFilterCounterText(filteredItemsCount)}
          expandToViewport={true}
        />
      }
      pagination={<Pagination {...paginationProps} />}
    />
  );
}
