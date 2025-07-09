import { useContext, useEffect, useState } from "react";
import AxiosApi from "../../api/AxiosApi";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import type { ContractItem } from "../model/contract";
import { ContractContext } from "../../api/provider/SearchProvider";
import { PageNavigate } from "../../common/PageNavigate";
import ContractModal from "./modal/ContractModal";

const ContractList = () => {
  const [data, setData] = useState<ContractItem[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalCount, setTotalCount] = useState(0);
  const { searchKeyword } = useContext(ContractContext);
  const [certiModal, setCertiModal] = useState(false);
  const [contractId, setContractId] = useState(0);
  useEffect(() => {
    Contract();
  }, [searchKeyword, currentPage]);

  const formatDate = (dateStr: string) =>
    new Date(dateStr).toLocaleDateString("ko-KR");

  const formatMoney = (money: number) => money.toLocaleString("ko-KR") + "원";

  const Contract = async () => {
    console.log("검색어", searchKeyword);
    console.log("전체 페이지", totalCount);
    const res = await AxiosApi.contractApi(searchKeyword, currentPage, 5);
    setData(res.data.list);
    setTotalCount(res.data.totalCount);
    console.log("contract list", res);
  };
  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber); // 페이지 번호 업데이트 (1부터 시작)
  };
  const CertiModalState = (contract_id: number) => {
    setCertiModal(true);
    setContractId(contract_id);
  };
  const closeModal = () => {
    setCertiModal(false);
  };
  return (
    <>
      <TableContainer
        component={Paper}
        sx={{
          my: 3,
          minHeight: { xs: "300px", sm: "400px", md: "500px" },
          width: "100%",
          maxWidth: "100vw",
          boxSizing: "border-box",
          mx: { xs: 0, sm: "auto" },
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                보험명
              </TableCell>
              <TableCell>계약번호</TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                보험사
              </TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                계약일
              </TableCell>
              <TableCell>보험기간</TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                총 보험료
              </TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                상태
              </TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                계약자
              </TableCell>
              <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                소속 부서
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row) => (
              <TableRow
                key={row.contract_id}
                onClick={() => CertiModalState(row.contract_id)}
              >
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.product_name}
                </TableCell>
                <TableCell>{row.contract_no}</TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.co}
                </TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {formatDate(row.contract_date)}
                </TableCell>
                <TableCell>
                  {row.start_date && row.end_date
                    ? `${formatDate(row.start_date)} ~ ${formatDate(
                        row.end_date
                      )}`
                    : ""}
                </TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.total_insurance_pay > 0 &&
                    formatMoney(row.total_insurance_pay)}
                </TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.insurance_state}
                </TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.user_name}
                </TableCell>
                <TableCell sx={{ display: { xs: "none", sm: "table-cell" } }}>
                  {row.store}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <PageNavigate
          activePage={currentPage}
          itemsCountPerPage={5}
          totalItemsCount={totalCount} // 전체 항목 수 전달
          // pageRangeDisplayed={5}
          onChange={handlePageChange} // 페이지 변경 시 호출
        />
      </TableContainer>
      <ContractModal
        open={certiModal}
        close={closeModal}
        type={true}
        id={contractId}
      />
    </>
  );
};

export default ContractList;
