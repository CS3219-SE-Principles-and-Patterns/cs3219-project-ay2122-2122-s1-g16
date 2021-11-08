import React, { useEffect, useState } from 'react';
import { useHistory } from "react-router-dom";
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import VisibilityOutlinedIcon from '@mui/icons-material/VisibilityOutlined';
import { IconButton } from '@mui/material';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import { userGetHistory } from '../../../redux/userSlice';
import { parseDateTime, parseDifficultyLevel, parseHtml } from '../../../util/util';
import { loadState } from '../../../redux/localStateManagement';

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: '50%',
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 4,
  maxHeight: 800,
  overflow:'scroll',
};

const columns = [
  { id: 'attemptAt', label: 'Date Time', minWidth: 50, maxWidth: 100},
  { id: 'difficulty', label: 'Difficulty Level', minWidth: 50, maxWidth: 50},
  { id: 'title', label: 'Question Title', minWidth: 100, maxWidth: 150},
  { id: 'historyId', label: 'View Detail', minWidth: 50, maxWidth: 50},
];

function createRowMap(records) {
  var rows = new Map()
  for (var i = 0; i < records.length; i++) {    
    records[i].question = parseHtml(records[i].question)
    records[i].solution = parseHtml(records[i].solution)
    records[i].attemptedAnswer = parseHtml(records[i].attemptedAnswer)
    records[i].difficulty = parseDifficultyLevel(records[i].difficulty)
    records[i].attemptAt = parseDateTime(records[i].attemptAt)
    rows.set(records[i].historyId, records[i])
  }
  return rows;
}

export default function History() {
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(20);
  const [viewDtailTargetId, setViewDtailTargetId] = useState("0");
  const [open, setOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [displayRecords, setDisplayRecords] = useState({});
  const [recordsCount, setRecordsCount] = useState(0)
  const [recordsArray, setRecordsArray] = useState({});
  const [user, setUser] = useState({});
  const history = useHistory()

  useEffect(() => {
    async function loadUser() {
      setUser(await loadState('user'))
      if(user === null) {
        history.push("/login")
        return 
      }
    }
    loadUser()
  }, [])

  useEffect(() => {
    async function getHistory() {
      if (user !== undefined && user.id !== undefined) {
        if (await userGetHistory(user.id)) {
          var records = []
          records = await Promise.all(loadState('history'))
          var tempMap = Object.assign({}, Array.from(createRowMap(records)))
          setRecordsArray(tempMap)
        }
      } 
    }
    getHistory()
  },[user])

  useEffect(()=>{
      updateDisplayRecords()
      setIsLoading(false) 
      setRecordsCount(Object.values(recordsArray).length)
      console.log("recordsArray", recordsArray)
  }, [recordsArray])

  useEffect(()=>{
    setIsLoading(true)
    updateDisplayRecords();
  },[page, rowsPerPage])

  useEffect(()=>{
    setIsLoading(false)
  },[displayRecords])

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const updateDisplayRecords = () => {
    var temp = Object.assign({}, Object.values(recordsArray).slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage))
    setDisplayRecords(temp)
  }

  const handleViewDetail = (id) => {
    setViewDtailTargetId(id)
    setOpen(true)
  }

  const ViewDetailIconButton = (props) => {
    const id = props.id
    return (
      <div className="w-full flex justify-center">
        <IconButton 
        className="focus:outline-none"
        onClick={() => handleViewDetail(id)} size="large">
        <VisibilityOutlinedIcon 
            style={{color: "#808080"}}
            fontSize="inherit"
        />
      </IconButton>
      </div>
    )
  }

  const convertMap = () => {
    var tempMap = new Map()
    console.log(typeof recordsArray)
    for(var key in recordsArray) {
      tempMap.set(recordsArray[key][0],  recordsArray[key][1])
      console.log(recordsArray[key])
    }
    console.log(tempMap)
    return tempMap
  }

  const DetailModal = () => {
    if (!open) {
      return null
    }

    console.log(viewDtailTargetId)
    var detailTarget = convertMap().get(viewDtailTargetId)
    console.log(detailTarget)
    return (
    <Modal
        open={open}
        onClose={() => setOpen(false)}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
      <Paper sx={style}>
        <Typography id="modal-modal-title" variant="h6" component="h2">
         {detailTarget.title}
        </Typography>
        <Typography id="modal-modal-title" variant="body2" component="h2">
          <span>Attempted at: {detailTarget.attemptAt}</span>
          <span style={{float:"right", paddingRight:"20%"}}>Difficulty Level: {detailTarget.difficulty }</span>
        </Typography>
        <hr className="my-4"></hr>
        <Typography id="modal-modal-description" variant="body2" color="text.secondary" sx={{ mt: 2 }}>
          <b className="text-blueGray-900 text-xl mb-8">Question:</b><br/>
          <div dangerouslySetInnerHTML={{__html: detailTarget.question}}></div>
        </Typography>
        <Typography id="modal-modal-description" variant="body2" color="text.secondary" sx={{ mt: 2 }}>
          <b className="text-blueGray-900 text-xl mb-8">Attempted Answer:</b><br/>
          <div dangerouslySetInnerHTML={{__html: detailTarget.attemptedAnswer}}></div>
        </Typography>
        <hr className="my-4"></hr>
        <Typography id="modal-modal-description" variant="body2" color="text.secondary" sx={{ mt: 2 }}>
          <b className="text-blueGray-900 mb-8 text-xl">Solution:</b><br/>
          <div dangerouslySetInnerHTML={{__html: detailTarget.solution}}></div>
        </Typography> 
      </Paper>
    </Modal>
    )
  }
  // console.log(displayRecords)
  return (
    <div className="relative mx-auto min-w-0  mb-6 pt-8 shadow-lg rounded-lg bg-blueGray-200 border-0 h-screen80 w-full"
        style={{ minWidth: "900px", minHeight: "780px"}}>        
      <div className="pb-12" style={{maxHeight: "600px"}}>
        <Paper  sx={{ width: '90%', overflow: 'hidden', marginX:"5%" }}>
          {!isLoading ? <DetailModal ></DetailModal> : null}
          <TableContainer sx={{ maxHeight: "650px" }}>
            <Table stickyHeader aria-label="sticky table">
              <TableHead>
                <TableRow>
                  {columns.map((column) => (
                    <TableCell
                      key={column.id}
                      style={{ width: column.maxWidth, textAlign: "center", fontWeight: "bold"}}
                    >
                      {column.label}
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              {!isLoading && displayRecords !== {} ? 
                <TableBody>
                {Object.entries(displayRecords).map((row) => {
                    return (
                      <TableRow hover role="checkbox" tabIndex={-1} key={row[0]}>
                        {columns.map((column) => {
                          const value = eval('row[1][1].'+[column.id]);
                          return (
                            <TableCell key={column.id}>
                              {column.id === "historyId"
                                ? <ViewDetailIconButton id={value}></ViewDetailIconButton>
                                : <div className="text-center">{value}</div>}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  })}
              </TableBody>
              : null}   
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[20, 40, 100]}
            component="div"
            count={recordsCount}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Paper>
      </div>
    </div>
  );
}
