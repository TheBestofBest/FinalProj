"use client";

import StructureTables from "./StructureTables";

export default function BasicTable() {


  return (
    <div className="grid grid-cols-3 gap-10">
      <StructureTables category="divisions" categoryKo="소속"/>
      <StructureTables category="departments" categoryKo="부서"/>
      <StructureTables category="grades" categoryKo="직급"/>
    </div>
  );
}
