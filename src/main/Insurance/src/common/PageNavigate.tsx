import { Pagination } from "@mui/material";
import { PageNavigateStyled } from "./styled";
import type { FC } from "react";

export interface IPageNavigateProps {
  totalItemsCount: number; // 전체 아이템 수
  activePage: number; // 현재 페이지
  itemsCountPerPage: number; // 페이지당 아이템 수
  onChange: (pageNumber: number) => void; // 페이지 변경 핸들러
}

export const PageNavigate: FC<IPageNavigateProps> = ({
  totalItemsCount,
  onChange,
  activePage,
  itemsCountPerPage,
}) => {
  const totalPages = Math.ceil(totalItemsCount / itemsCountPerPage);

  const handleChange = (_: React.ChangeEvent<unknown>, value: number) => {
    onChange(value);
  };

  return (
    <PageNavigateStyled>
      <Pagination
        count={totalPages}
        page={activePage}
        onChange={handleChange}
        shape="rounded"
        color="primary"
        showFirstButton
        showLastButton
      />
    </PageNavigateStyled>
  );
};
