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
import ModifyStructure from "./ModifyStructure";
import DeleteStructure from "./DeleteStructure";

interface Props {
  category: string;
  categoryKo: string;
}

export default function StructureTables({ category, categoryKo }: Props) {
  const queryClient = useQueryClient();

  const getData = async () => {
    const response = await api.get(`/api/v1/${category}`);
    return response.data.data.dtoList;
  };

  console.log(getData());

  const { data }: any = useQuery({
    queryKey: [category],
    queryFn: getData, // 쿼리 함수 정의
  });

  return (
    <div>
      <div className="m-2 flex justify-between">
        <div className="text-2xl font-bold">{categoryKo}</div>
        <AddStructure category={category} categoryKo={categoryKo} />
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
                    {index}
                  </TableCell>
                  <TableCell className="dark:text-white" align="center">
                    {row.code}
                  </TableCell>
                  <TableCell className="dark:text-white" align="center">
                    {row.name}
                  </TableCell>

                  <TableCell>
                    {row.code !== 0 && (
                      <div className="flex justify-end gap-3">
                        <ModifyStructure
                          row={row}
                          category={category}
                          categoryKo={categoryKo}
                        />
                        <DeleteStructure
                          row={row}
                          category={category}
                          categoryKo={categoryKo}
                        />
                      </div>
                    )}
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}
