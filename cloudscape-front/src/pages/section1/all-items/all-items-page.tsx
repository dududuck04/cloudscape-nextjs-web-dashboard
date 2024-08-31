import { BreadcrumbGroup } from "@cloudscape-design/components";
import { APP_NAME } from "../../../common/constants.ts";
import { useOnFollow } from "../../../common/hooks/use-on-follow.ts";
import BaseAppLayout from "../../../components/base-app-layout.tsx";
import AllItemsTable from "./all-items-table.tsx";

export default function AllItemsPage() {
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
            {
              text: "Security Group",
              href: "/section1",
            },
          ]}
        />
      }
      content={<AllItemsTable />}
    />
  );
}
