let csrfToken=null;let csrfPending=null;
export const roleNames={CUSTOMER:'Customer',DESIGNER:'Interior Designer',USER_ADMIN:'User Admin',PLATFORM_MANAGER:'Platform Management'};
export const money=n=>'S$'+Number(n||0).toLocaleString();
export async function request(path,{method='GET',body}={}) {
 const headers={Accept:'application/json'};
 if(method!=='GET') {if(!csrfToken){csrfPending??=fetch('/api/auth/csrf',{credentials:'same-origin'}).then(r=>{if(!r.ok)throw new Error('Could not establish a secure session');return r.json()}).then(d=>{csrfToken=d.token;return d.token}).finally(()=>{csrfPending=null});await csrfPending;}headers['X-CSRF-Token']=csrfToken;}
 if(body!==undefined)headers['Content-Type']='application/json';
 const response=await fetch('/api/'+path,{method,headers,credentials:'same-origin',body:body===undefined?undefined:JSON.stringify(body)});
 const text=await response.text();let data=null;try{data=text?JSON.parse(text):null}catch{throw new Error('The server returned an unexpected response. Is the backend running?')}
 if(!response.ok){if(response.status===401&&typeof window!=='undefined')window.dispatchEvent(new Event('forma:unauthorized'));throw new Error(data?.message||'Request failed ('+response.status+')');}
 return data;
}
export const api={
 me:()=>request('auth/me'),demos:()=>request('auth/demo-users'),login:d=>request('auth/login',{method:'POST',body:d}),demo:d=>request('auth/demo-login',{method:'POST',body:d}),workspace:workspace=>request('auth/workspace',{method:'POST',body:{workspace}}),
 logout:async()=>{try{return await request('auth/logout',{method:'POST',body:{}})}finally{csrfToken=null}},
 signup:d=>request('auth/signup',{method:'POST',body:d}),myRequests:()=>request('auth/signup-status'),
 list:(resource,params={})=>request(resource+'?'+new URLSearchParams(Object.entries(params).filter(([,v])=>v!==undefined&&v!==''))),get:(resource,id)=>request(resource+'/'+id),
 save:(resource,id,body)=>request(resource+(id?'/'+id:''),{method:id?'PUT':'POST',body}),remove:(resource,id)=>request(resource+'/'+id,{method:'DELETE'}),suspend:(resource,id)=>request(resource+'/'+id+'/suspension',{method:'PATCH',body:{}}),
 shortlist:()=>request('favourites'),saveFavourite:id=>request('favourites/'+id,{method:'POST',body:{}}),removeFavourite:id=>request('favourites/'+id,{method:'DELETE'}),recordView:id=>request('projects/'+id+'/views',{method:'POST',body:{}}),
 approve:(id,role)=>request('registrations/'+id+'/approve',{method:'POST',body:{role}}),reject:id=>request('registrations/'+id+'/reject',{method:'POST',body:{}}),report:period=>request('reports/'+period)
};
