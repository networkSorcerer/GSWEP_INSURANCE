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

const ContractList = () => {
  const [data, setData] = useState<ContractItem[]>([]);

  const { searchKeyword } = useContext(ContractContext);

  useEffect(() => {
    Contract();
  }, [searchKeyword]);

  const formatDate = (dateStr: string) =>
    new Date(dateStr).toLocaleDateString("ko-KR");

  const formatMoney = (money: number) => money.toLocaleString("ko-KR") + "원";

  const Contract = async () => {
    console.log("검색어", searchKeyword);
    const res = await AxiosApi.contractApi(searchKeyword);
    setData(res.data.list);
    console.log("contract list", res);
  };

  return (
    <TableContainer component={Paper} sx={{ my: 3 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>보험명</TableCell>
            <TableCell>계약번호</TableCell>
            <TableCell>보험사</TableCell>
            <TableCell>계약일</TableCell>
            <TableCell>보험기간</TableCell>
            <TableCell>총 보험료</TableCell>
            <TableCell>상태</TableCell>
            <TableCell>계약자</TableCell>
            <TableCell>소속 부서</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row) => (
            <TableRow key={row.contract_id}>
              <TableCell>{row.product_name}</TableCell>
              <TableCell>{row.contract_no}</TableCell>
              <TableCell>{row.co}</TableCell>
              <TableCell>
                {row.contract_date && formatDate(row.contract_date)}
              </TableCell>
              <TableCell>
                {row.start_date && row.end_date
                  ? `${formatDate(row.start_date)} ~ ${formatDate(
                      row.end_date
                    )}`
                  : ""}
              </TableCell>
              <TableCell>
                {row.total_insurance_pay > 0 &&
                  formatMoney(row.total_insurance_pay)}
              </TableCell>
              <TableCell>{row.insurance_state}</TableCell>
              <TableCell>{row.user_name}</TableCell>
              <TableCell>{row.store}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ContractList;
