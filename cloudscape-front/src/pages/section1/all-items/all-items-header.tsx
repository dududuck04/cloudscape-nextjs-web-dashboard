import {
  Button,
  Header,
  HeaderProps,
  SpaceBetween,
} from "@cloudscape-design/components";
import { Item } from "../../../common/types.ts";

interface AllItemsPageHeaderProps extends HeaderProps {
  title?: string;
  createButtonText?: string;
  selectedItems: readonly Item[];
}

export function AllItemsPageHeader({
  title = "Security Groups",
  ...props
}: AllItemsPageHeaderProps) {

  return (
    <Header
      variant="awsui-h1-sticky"
      actions={
        <SpaceBetween size="xs" direction="horizontal">
          <Button iconName="refresh" />
        </SpaceBetween>
      }
      {...props}
    >
      {title}
    </Header>
  );
}

export default AllItemsPageHeader;