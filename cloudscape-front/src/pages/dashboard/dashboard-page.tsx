import { APP_NAME } from "../../common/constants.ts";
import {
  BreadcrumbGroup,
  ContentLayout,
  SpaceBetween,
} from "@cloudscape-design/components";
import { useOnFollow } from "../../common/hooks/use-on-follow.ts";
import BaseAppLayout from "../../components/base-app-layout.tsx";
import DashboardHeader from "./dashboard-header.tsx";
import ItemsTable from "./items-table.tsx";

export default function DashboardPage() {
  const onFollow = useOnFollow();

  return (
    <BaseAppLayout
      breadcrumbs={
        <BreadcrumbGroup
          onFollow={onFollow}
          items={[
            {
              text: APP_NAME,
              href: "/",
            },
          ]}
        />
      }
      content={
        <ContentLayout header={<DashboardHeader />}>
          <SpaceBetween size="l">
            {/*<StatisticsBlock />*/}
            <ItemsTable />
          </SpaceBetween>
        </ContentLayout>
      }
    />
  );
}
