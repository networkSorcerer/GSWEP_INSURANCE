import { ContractProvider } from "../../api/provider/SearchProvider";
import ContractList from "./ContractList";
import { ContractSearch } from "./ContractSearch/ContractSearch";

const Contracts = () => {
  return (
    <div>
      <ContractProvider>
        <ContractSearch></ContractSearch>
        <ContractList></ContractList>
      </ContractProvider>
    </div>
  );
};

export default Contracts;
