import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import api from "@/util/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import AddStructure from "./AddStructure";
import Loader from "../common/Loader";

interface Props{
    category:string;
    categoryKo:string;
}

export default function StructureTables({category, categoryKo}: Props){
    const queryClient = useQueryClient();

    const getData = async () => {
      const response = await api.get(`/api/v1/${category}`);
      return response.data.dtoList;
    };
  
    const { data }:any = useQuery({
      queryKey: [category],
      queryFn: getData,
    });

    return(<div>
        <div className="m-2 flex justify-between">
          <div className="text-2xl font-bold">{categoryKo}</div>
          <AddStructure  category={category} categoryKo={categoryKo}/>
        </div>
        <TableContainer
          component={Paper}
          className="dark:border dark:border-white dark:bg-boxdark"
        >
          <Table>
            <TableHead>
              <TableRow>
                <TableCell className="dark:text-white" align="center">
                  번호
                </TableCell>
                <TableCell className="dark:text-white" align="center">
                  {categoryKo} 코드
                </TableCell>
                <TableCell className="dark:text-white" align="center">
                  {categoryKo}명
                </TableCell>
                <TableCell></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {queryClient
                .getQueryData<any>([category])
                ?.map((row: any, index: number) => (
                  <TableRow
                    key={index}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell
                      className="dark:text-white"
                      component="th"
                      scope="row"
                      align="center"
                    >
                      {index + 1}
                    </TableCell>
                    <TableCell className="dark:text-white" align="center">
                      {row.code}
                    </TableCell>
                    <TableCell className="dark:text-white" align="center">
                      {row.name}
                    </TableCell>
                    <TableCell>
                      <div className="flex justify-end gap-3">
                        <button>
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            height="24px"
                            viewBox="0 -960 960 960"
                            width="24px"
                            fill="#5f6368"
                          >
                            <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z" />
                          </svg>
                        </button>
                        <button>
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            height="24px"
                            viewBox="0 -960 960 960"
                            width="24px"
                            fill="#5f6368"
                          >
                            <path d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z" />
                          </svg>
                        </button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>);
}