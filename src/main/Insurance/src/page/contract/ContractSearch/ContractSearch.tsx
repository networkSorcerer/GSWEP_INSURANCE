import { useContext, useState } from "react";
import { ContractContext } from "../../../api/provider/SearchProvider";
import { ContractSearchStyled } from "../../../layout/styled/contract";
import { Button, Typography } from "@mui/material";

export const ContractSearch = () => {
  const { setSearchKeyword } = useContext(ContractContext);
  const [input, setInput] = useState<{
    oname: string;
    sname: string;
  }>({ oname: "contract_no", sname: "" });

  const handlerSearch = () => {
    setSearchKeyword(input);
  };
  return (
    <>
      <Typography variant="h4">계약정보</Typography>
      <ContractSearchStyled>
        <select
          onChange={(e) => setInput({ ...input, oname: e.currentTarget.value })}
        >
          <option value={"contract_no"}>계약 번호</option>
          <option value={"member_name"}>사용자 이름</option>
        </select>
        <input
          onChange={(e) => setInput({ ...input, sname: e.target.value })}
        ></input>
        <Button onClick={handlerSearch}>검색</Button>
      </ContractSearchStyled>
    </>
  );
};
