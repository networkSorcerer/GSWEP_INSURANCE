export interface ContractItem {
  contract_id: number;
  product_code: string;
  product_name: string;
  contract_no: number;
  co: string;
  contract_date: string; // 또는 Date
  start_date: string;
  end_date: string;
  total_insurance_pay: number;
  insurance_state: string;
  user_name: string;
  store: string;
}

export interface getContractResponse {
  data: {
    list: ContractItem[];
    totalCount: number;
  };
}

export interface searchKeyword {
  oname: String;
  sname: String;
}

// Context 전체 타입 정의
export interface ContractContextType {
  searchKeyword: searchKeyword;
  setSearchKeyword: React.Dispatch<React.SetStateAction<searchKeyword>>;
}
