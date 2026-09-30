import React,{useEffect,useState} from 'react';
import {createRoot} from 'react-dom/client';
import './style.css';

const API='http://localhost:8080/api';
const demoAssets=[
 {id:1,name:'Armored Vehicle',type:'Vehicle',baseName:'North Base',quantity:25,status:'Available'},
 {id:2,name:'Rifle',type:'Weapon',baseName:'East Base',quantity:80,status:'Available'},
 {id:3,name:'Radio',type:'Communication',baseName:'West Base',quantity:40,status:'Available'},
 {id:4,name:'Truck',type:'Vehicle',baseName:'South Base',quantity:12,status:'Maintenance'}
];

function App(){
 const [user,setUser]=useState(JSON.parse(localStorage.getItem('assetUser')||'null'));
 const [page,setPage]=useState('Dashboard');
 const [assets,setAssets]=useState(demoAssets),[purchases,setPurchases]=useState([]),[transfers,setTransfers]=useState([]);
 const [refresh,setRefresh]=useState(0);
 useEffect(()=>{if(!user)return; Promise.all([
 fetch(API+'/assets').then(r=>r.ok?r.json():[]).catch(()=>[]),
 fetch(API+'/purchases').then(r=>r.ok?r.json():[]).catch(()=>[]),
 fetch(API+'/transfers').then(r=>r.ok?r.json():[]).catch(()=>[])
 ]).then(([a,p,t])=>{if(a.length)setAssets(a);setPurchases(p);setTransfers(t)});},[user,refresh]);
 if(!user)return <Login onLogin={u=>{localStorage.setItem('assetUser',JSON.stringify(u));setUser(u)}}/>;
 const logout=()=>{localStorage.removeItem('assetUser');setUser(null)};
 return <div className="app">
   <aside><div className="brand">🛡️ <span>AssetTrack</span></div>
    {['Dashboard','Purchases','Transfers','Assets','Audit Logs'].map(x=><button className={page===x?'nav active':'nav'} onClick={()=>setPage(x)} key={x}>{x}</button>)}
    <div className="sidebottom">Logged in as<br/><b>{user.username}</b><br/><small>{user.role.replaceAll('_',' ')}</small><button className="logout" onClick={logout}>Logout</button></div>
   </aside>
   <main><header><div><h2>{page}</h2><p>Asset management and movement tracking</p></div><div className="user">👤 {user.username} ▾</div></header>
   {page==='Dashboard'&&<Dashboard assets={assets} purchases={purchases} transfers={transfers}/>}
   {page==='Purchases'&&<Purchases purchases={purchases} refresh={()=>setRefresh(x=>x+1)}/>}
   {page==='Transfers'&&<Transfers transfers={transfers} refresh={()=>setRefresh(x=>x+1)}/>}
   {page==='Assets'&&<Assets assets={assets}/>}
   {page==='Audit Logs'&&<Audit/>}
   </main>
 </div>
}

function Login({onLogin}){const [u,setU]=useState('admin'),[p,setP]=useState('admin'),[err,setErr]=useState('');
 async function go(e){e.preventDefault();try{let r=await fetch(API+'/auth/login',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({username:u,password:p})});if(!r.ok)throw 0;onLogin(await r.json())}catch{setErr('Use admin/admin, commander/commander or logistics/logistics')}}return <div className="login"><div className="loginCard"><div className="loginSide"><div className="bigbrand">🛡️ AssetTrack</div><p>Manage assets, purchases and transfers across bases.</p></div><form onSubmit={go}><h1>Welcome Back</h1><p>Sign in to your account</p><label>Username</label><input value={u} onChange={e=>setU(e.target.value)}/><label>Password</label><input type="password" value={p} onChange={e=>setP(e.target.value)}/><button className="primary">Login</button>{err&&<div className="error">{err}</div>}<div className="demo">Demo: admin / admin</div></form></div></div>}

function Dashboard({assets,purchases,transfers}){const total=assets.reduce((s,a)=>s+(a.quantity||0),0),pq=purchases.reduce((s,p)=>s+(p.quantity||0),0),tq=transfers.reduce((s,t)=>s+(t.quantity||0),0);return <><div className="filters"><input type="date"/><select><option>All Bases</option><option>North Base</option><option>East Base</option></select><select><option>All Equipment</option><option>Vehicles</option><option>Weapons</option></select><button className="primary">Apply</button></div><div className="cards"><Card t="Total Assets" n={total}/><Card t="Purchases" n={pq}/><Card t="Net Movement" n={pq+tq}/><Card t="Assigned Assets" n={Math.floor(total/2)}/><Card t="Expended Assets" n={Math.floor(total/10)}/></div><div className="grid"><section className="panel"><h3>Asset Overview</h3><div className="bars"><div style={{width:'78%'}}>Available <b>{total}</b></div><div style={{width:'52%'}}>Purchased <b>{pq}</b></div><div style={{width:'36%'}}>Transferred <b>{tq}</b></div></div></section><section className="panel"><h3>Recent Activity</h3><p>🟢 Purchase records: {purchases.length}</p><p>🔵 Transfer records: {transfers.length}</p><p>🟣 Assets tracked: {assets.length}</p></section></div></>}

function Card({t,n}){return <div className="card"><span>{t}</span><strong>{n}</strong></div>}

function Purchases({purchases,refresh}){const [form,setForm]=useState({baseName:'North Base',equipmentType:'Vehicle',quantity:1,supplier:''});async function add(){await fetch(API+'/purchases',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({...form,quantity:Number(form.quantity)})});setForm({...form,quantity:1,supplier:''});refresh()}return <><div className="toolbar"><h3>Purchase History</h3><button className="primary" onClick={add}>+ Add Purchase</button></div><div className="formrow"><select value={form.baseName} onChange={e=>setForm({...form,baseName:e.target.value})}><option>North Base</option><option>East Base</option><option>West Base</option><option>South Base</option></select><select value={form.equipmentType} onChange={e=>setForm({...form,equipmentType:e.target.value})}><option>Vehicle</option><option>Weapon</option><option>Communication</option><option>Equipment</option></select><input type="number" min="1" value={form.quantity} onChange={e=>setForm({...form,quantity:e.target.value})}/><input placeholder="Supplier" value={form.supplier} onChange={e=>setForm({...form,supplier:e.target.value})}/></div><Table headers={['ID','Date','Equipment','Qty','Base','Supplier']} rows={purchases.map(x=>[x.id,new Date(x.createdAt).toLocaleDateString(),x.equipmentType,x.quantity,x.baseName,x.supplier||'-'])}/></>}

function Transfers({transfers,refresh}){const [f,setF]=useState({fromBase:'North Base',toBase:'East Base',equipmentType:'Vehicle',quantity:1});async function add(){if(f.fromBase===f.toBase)return alert('From and To base must be different');await fetch(API+'/transfers',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({...f,quantity:Number(f.quantity)})});refresh()}return <><div className="toolbar"><h3>Transfer History</h3><button className="primary" onClick={add}>+ New Transfer</button></div><div className="formrow"><select value={f.fromBase} onChange={e=>setF({...f,fromBase:e.target.value})}><option>North Base</option><option>East Base</option><option>West Base</option><option>South Base</option></select><select value={f.toBase} onChange={e=>setF({...f,toBase:e.target.value})}><option>East Base</option><option>North Base</option><option>West Base</option><option>South Base</option></select><select value={f.equipmentType} onChange={e=>setF({...f,equipmentType:e.target.value})}><option>Vehicle</option><option>Weapon</option><option>Communication</option></select><input type="number" min="1" value={f.quantity} onChange={e=>setF({...f,quantity:e.target.value})}/></div><Table headers={['ID','Date','Equipment','Qty','From','To','Status']} rows={transfers.map(x=>[x.id,new Date(x.createdAt).toLocaleDateString(),x.equipmentType,x.quantity,x.fromBase,x.toBase,x.status])}/></>}

function Assets({assets}){return <><div className="filters"><input placeholder="Search assets..."/><select><option>All Bases</option></select><select><option>All Types</option></select></div><Table headers={['ID','Name','Type','Base','Qty','Status']} rows={assets.map(x=>[x.id,x.name,x.type,x.baseName,x.quantity,x.status])}/></>}
function Audit(){const [logs,setLogs]=useState([]);useEffect(()=>{fetch(API+'/audit-logs').then(r=>r.json()).then(setLogs).catch(()=>{})},[]);return <Table headers={['Date','User','Role','Action','Details']} rows={logs.map(x=>[new Date(x.createdAt).toLocaleString(),x.username,x.role,x.action,x.details])}/>}
function Table({headers,rows}){return <div className="tableWrap"><table><thead><tr>{headers.map(h=><th key={h}>{h}</th>)}</tr></thead><tbody>{rows.length?rows.map((r,i)=><tr key={i}>{r.map((c,j)=><td key={j}>{c}</td>)}</tr>):<tr><td colSpan={headers.length}>No records yet</td></tr>}</tbody></table></div>}

createRoot(document.getElementById('root')).render(<App/>);
