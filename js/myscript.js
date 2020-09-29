function $$$$$(elemt)
{
 var element=elemt;
 this.val=function(){
  if(element.value) return element.value;
  else throw "component doesn't have value property";
 }
 this.html=function(content){
  element.innerHTML=content;
 }
}


$$$=function(name){
 var elemt;
 this.queryString="";
 this.queryParameterString="";
 if(name.startsWith('#'))
 {
  elemt=document.getElementById(name.substring(1));
  return new $$$$$(elemt);
 }
 if(name.startsWith('.')) // '.' consider as a single element access
 {
  elemt=document.querySelector(name);
  return new $$$$$(elemt);
 }
 elemt=document.querySelector(name);
 if(!elemt)return new $$$$$(elemt);
 else "component doesn't exists"
};



$$$.postJSON=function(jsonObject){
 if(!jsonObject.url)
 {
  throw "url property missing in json/url is boolean";
 }
 if(typeof jsonObject.url!="string")
 {
  throw "url property should be of string type";
 }
 if(jsonObject.success && typeof jsonObject.success!="function")
 {
  throw "success property should represent a function";
 }
 if(jsonObject.exception && typeof jsonObject.exception!="function")
 {
  throw "exception property should represent a function";
 }
 if(jsonObject.faild && typeof jsonObject.faild!="function")
 {
  throw "faild property should represent a function";
 }
 if(jsonObject.queryParameter && typeof jsonObject.queryParameter!="object")
 {
  //alert("type of queryParameter : "+typeof jsonObject.queryParameter);
  throw "queryParameter property should represent a object/json type";
 }

 var xmlHttpRequest=new XMLHttpRequest();
 xmlHttpRequest.onreadystatechange=function(){
  if(this.readyState==4)
  {
   if(this.status==200)
   {
    var responseString=xmlHttpRequest.responseText;
    var responseJSON=JSON.parse(responseString);
    if(responseJSON.success)
    {
     jsonObject.success(responseJSON.response);
    }
    else
    {
     jsonObject.exception(responseJSON.exception);
    }
   }
   else
   {
    if(jsonObject.faild)jsonObject.faild();
   }
  }
 }
 xmlHttpRequest.open("POST",jsonObject.url,true);
 if(jsonObject.queryParameter)
 {
  xmlHttpRequest.setRequestHeader("Content-type", "application/json");
  xmlHttpRequest.send(JSON.stringify(jsonObject.queryParameter));
 }
 else xmlHttpRequest.send();
}













function BindComponentNode()
{
 this.lastValueOfProperty="";
 this.lastValueOfComponent="";
 this.property="";
 this.component="";
 this.object=null;
}
function TableComponentNode()
{
  this.lastValueOfProperty="";
  this.property="";
  this.model="";
  this.table=null;
  this.object=null;
}
var bindComponentList=[];
$$$.setModel=function(obj)
{
  var properties=Object.keys(obj);
  // Object.getOwnPropertyNames(obj)
  console.log(properties);
  // var list=document.querySelectorAll("input[tmf-bind]");
  //  var list=document.querySelectorAll("[tmf-bind='"+property[0]+"']");

  var list=document.querySelectorAll("[tmf-bind]");
  console.log(list);
  var i=0;
  var k=0;
  while(i<list.length)
  {
   for(const key of properties)
   {
     if(list[i].getAttribute("tmf-bind")==key)
     {
       if(list[i].tagName=="TABLE")
       {
	 //alert("Table Component");
         var tableComponentNode=new TableComponentNode();
         tableComponentNode.lastValueOfProperty=obj[key];
         tableComponentNode.property=key;
         tableComponentNode.model=list[i].getAttribute("tableModel");
         tableComponentNode.table=list[i];
         tableComponentNode.object=obj;
	 bindComponentList[k++]=tableComponentNode;
         console.log(list[i].id+" --- "+list[i].getAttribute("tmf-bind"));
         console.log(tableComponentNode);
       }
       if(list[i].tagName=="INPUT")
       {
	 //alert("input Component");
         var bindComponentNode=new BindComponentNode();
         bindComponentNode.lastValueOfProperty=obj[key];
         bindComponentNode.lastValueOfComponent=list[i].value;
         bindComponentNode.property=key;
         bindComponentNode.component=list[i];
         bindComponentNode.object=obj;
         bindComponentList[k++]=bindComponentNode;
         console.log(list[i].id+" --- "+list[i].getAttribute("tmf-bind"));
         //list[i].oninput=function(){return updateDataStructure(this)};
         console.log(bindComponentNode);
       }
     }
   }
   i++;
  }
  console.log("DS length"+bindComponentList.length);
  console.log(bindComponentList);
  setInterval(observationStart,500);
}


function observationStart()
{
 //console.log("Hello "+bindComponentList);
 
 for(var x=0;x<bindComponentList.length;x++)
 {
  if(bindComponentList[x] instanceof BindComponentNode)
  {
   if(bindComponentList[x].component.value!=bindComponentList[x].lastValueOfComponent)
   {
    //alert("com chala");
    bindComponentList[x].object[bindComponentList[x].property]=bindComponentList[x].component.value;
    bindComponentList[x].lastValueOfProperty=bindComponentList[x].component.value;
    bindComponentList[x].lastValueOfComponent=bindComponentList[x].component.value;
   }
   if(bindComponentList[x].object[bindComponentList[x].property]!=bindComponentList[x].lastValueOfProperty)
   {
    //alert("obj chala");
    bindComponentList[x].component.value=bindComponentList[x].object[bindComponentList[x].property];
    bindComponentList[x].lastValueOfComponent=bindComponentList[x].object[bindComponentList[x].property];
    bindComponentList[x].lastValueOfProperty=bindComponentList[x].object[bindComponentList[x].property];
   }
  }
  if(bindComponentList[x] instanceof TableComponentNode)
  {
    if(JSON.stringify(bindComponentList[x].object[bindComponentList[x].property])!=JSON.stringify(bindComponentList[x].lastValueOfProperty))
    {
      bindComponentList[x].lastValueOfProperty=bindComponentList[x].object[bindComponentList[x].property];

      var table=bindComponentList[x].table;
      table.innerHTML='';
      var tableHeader=document.createElement('thead');
      tableHeader.classList.add("thead-light","shadow");
      var tableHeaderRow=document.createElement('tr');
      var modelName=bindComponentList[x].model;
      var modelObject=bindComponentList[x].object[modelName];
      var rows=modelObject.getRows();
      var columns=modelObject.getColumns();
      var td,th,tr;
      for(var i=0;i<columns;i++)
      {
        th=document.createElement('th');
        th.innerHTML=modelObject.getColumnTitle(i);
        tableHeaderRow.appendChild(th);
      }
      tableHeader.appendChild(tableHeaderRow);
      table.appendChild(tableHeader);

      var tableBody=document.createElement('tbody');
      for(var i=0;i<rows;i++)
      {
    	tr=document.createElement('tr');
        for(var j=0;j<columns;j++)
        {
          td=document.createElement('td');
          if((typeof modelObject.getValueAt(i,j))=="object")
          {
            td.appendChild(modelObject.getValueAt(i,j));  
	  }
          else
	  {
            td.innerHTML=modelObject.getValueAt(i,j);
	  }
          tr.appendChild(td);
        }
        tableBody.appendChild(tr);
      }
      table.appendChild(tableBody);
    }
  }
 }
  //setTimeout(observer,500);
}

$$$.setHtmlModel=function(obj)
{
 //alert("Model Chala");
 var htmlBind=[];
 var list=Object.keys(obj);
 console.log(list);
 var allDocument = document.querySelectorAll("*");
 var k=0;
 for(var i=0;i<list.length;i++)
 {
  for(var j=0;j<allDocument.length;j++)
  {
   //alert(allDocument[j].innerHTML);
   if(allDocument[j].innerHTML.includes('{{{'+list[i]+'}}}'))
   {
    //alert("Included");
    var flag=false;
    Array.from(allDocument[j].children).filter(function (child){
     if(child.innerHTML.includes('{{{'+list[i]+'}}}'))
     {
       flag=true;
     }
    });
    if(flag==false)
    {
     //alert("chala"+list[i]+","+allDocument[j]);
     htmlBind.push({object:obj,property:list[i],component:allDocument[j],lastValueOfProperty:obj[list[i]]});
     //alert(htmlBind);
     console.log(htmlBind);
     allDocument[j].innerHTML=allDocument[j].innerHTML.replace('{{{'+list[i]+'}}}','<span id="{{{'+(list[i]+k)+'}}}"></span>');
     //alert(list[i]+k);
     var objectValue=obj[list[i]];
     if(objectValue!=undefined && objectValue!=null && objectValue.length!=0)
      document.getElementById('{{{'+(list[i]+k)+'}}}').innerHTML=objectValue;
     k++;
    }  
   }
  }
 }
 setInterval(function(){updateHtmlModel(htmlBind)},500);
}

function updateHtmlModel(bind)
{
 //alert("updateHtmlModel chala");
 for(var i=0;i<bind.length;i++)
 {
  //alert(bind[i].lastValueOfProperty+","+bind[i]object[[bind[i].property]]);
  if(bind[i].lastValueOfProperty!=bind[i].object[[bind[i].property]])
  {
   var objectValue=bind[i].object[[bind[i].property]];
   if(objectValue!=undefined && objectValue!=null && objectValue.length!=0)
    document.getElementById('{{{'+(bind[i].property+i)+'}}}').innerHTML=objectValue;
   bind[i].lastValueOfProperty=bind[i].object[[bind[i].property]];
  }
 }
}



function processVariableInLoop()
{
  var list=document.querySelectorAll("[tmf-for]");
  for(var i=0;i<list.length;i++)
  {
    processVariable(list[i]);
  } 
}
function processVariable(domElement)
{
  var component=domElement;
  bindValue=component.getAttribute("tmf-for");
  if(bindValue==null)return;
  var values=bindValue.split(" ");

  var variableName="";
  var start="";
  var sep="";
  var end="";
  var step="";
  
  if(values[0].indexOf('=')!=-1)
  {
    variableName=values[0].substring(0,values[0].indexOf('='));
    if(variableName.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
    if(variableName.toUpperCase()=="in".toUpperCase())throw "doesn't use predefined keyword in tag "+component.localName+" :: "+bindValue;
    if(variableName.toUpperCase()=="to".toUpperCase())throw "doesn't use predefined keyword in tag "+component.localName+" :: "+bindValue;
    //alert(variableName);

    start=values[0].substring(values[0].indexOf('=')+1);
    if(start.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
    //alert(start);

    sep=values[1];
    if(sep.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
    if(sep.toUpperCase()=="in".toUpperCase())throw "syntax error in tag "+component.localName+" :: "+bindValue;
    //alert(sep);

    end=values[2];
    if(end.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
    //alert(start);
    
    if(values[3]!=null && values[3]!=undefined)
    {
      step=values[4];
      if(step.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
    }
    else
    {
      step=1;
    }
    start=Number(start);
    end=Number(end);
    step=Number(step);
    
    var documentFragment=document.createDocumentFragment();
    var parent=component.parentNode;
    component.removeAttribute("tmf-for");
    for(var j=start;j<=end;j+=step)
    {
      var componentClone=component.cloneNode(true);
      var childs=componentClone.querySelectorAll("[tmf-for]");
      for(var k=0;k<childs.length;k++)
      {
         processVariable(childs[k])
      }
      componentClone.innerHTML=componentClone.innerHTML.replace('{{{'+variableName+'}}}',j+"")
      documentFragment.appendChild(componentClone);
    }
    parent.replaceChild(documentFragment,component);

  }

}
window.addEventListener("load",processVariableInLoop);


function TableRowComponentNode()
{
  this.lastValueOfProperty="";
  this.property="";
  this.tr=null;
  this.object=null;
}

$$$.setTRLoopModel=function(obj)
{
  var bind=[];
  var properties=Object.keys(obj);
  //console.log(properties);
  var list=document.querySelectorAll("[tm-for]");
  //console.log(list);
  var i=0;
  var k=0;
  while(i<list.length)
  {
      var component=list[i];
      var bindValue=component.getAttribute("tm-for");
      
      if(bindValue==null)return;
      var values=bindValue.split(" ");

      var variableName="";
      var start="";
      var sep="";
      var end="";
      var step="";
  
      if(!(values[0].indexOf('=')!=-1))
      {
        variableName=values[0];
        if(variableName.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
        if(variableName.toUpperCase()=="in".toUpperCase())throw "doesn't use predefined keyword in tag "+component.localName+" :: "+bindValue;
        if(variableName.toUpperCase()=="to".toUpperCase())throw "doesn't use predefined keyword in tag "+component.localName+" :: "+bindValue;
        

        start=0;

        sep=values[1];
        if(sep.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
        if(sep.toUpperCase()=="to".toUpperCase())throw "syntax error in tag "+component.localName+" :: "+bindValue;
        if(sep.toUpperCase()!="in".toUpperCase())throw "syntax error in tag "+component.localName+" :: "+bindValue;
        

        if(values[2]==undefined || values[2].length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
        
        var flag=false;
        for(const key of properties)
        {
           if(key==values[2])
           {
              var tableRowComponentNode=new TableRowComponentNode();
              tableRowComponentNode.lastValueOfProperty=obj[key];
              tableRowComponentNode.property=key;
              tableRowComponentNode.tr=list[i];
              tableRowComponentNode.object=obj;
	      bind.push(tableRowComponentNode);

              //console.log(tableRowComponentNode);
              //alert("success");
              flag=true;
           }
        }
        if(!flag)throw "property doesn't exists in component in tag "+component.localName+" :: "+bindValue;

      }
    i++;
  } 
  setInterval(function(){obsr(bind)},500)
}
function obsr(bind)
{
//console.log("Bind Element length : "+bind.length);
  for(var x=0;x<bind.length;x++)
  {
    if(bind[x] instanceof TableRowComponentNode)
    {
       var tableRowComNode=bind[x];
       if(JSON.stringify(tableRowComNode.object[tableRowComNode.property])!=JSON.stringify(tableRowComNode.lastValueOfProperty))
       {
         //alert("change");
         var component=tableRowComNode.tr;
         //console.log(component);
         var bindValue=component.getAttribute("tm-for");
         if(bindValue==null)return;
         var values=bindValue.split(" ");
         var variableName=values[0];
         var start=0;
         var sep=values[1];
         var end=tableRowComNode.object[tableRowComNode.property].length;

         var step=1;
         var list=tableRowComNode.object[tableRowComNode.property];
         //console.log(list);
         var documentFragment=document.createDocumentFragment();
         var parent=component.parentNode;
         //component.removeAttribute("tm-for");
         for(var j=start;j<end;j+=step)
         {
           var componentClone=component.cloneNode(true);
           var childs=componentClone.children;
           //console.log(childs);
           var employee=list[j];
           var keys=Object.keys(list[0]);
           for(var k=0;k<childs.length;k++)
           {
/*Method 1
             var innerString=childs[k].innerHTML;
             for(key of keys)
             {
              if(innerString.includes('{{{'+variableName+'.'+key+'}}}'))
              {
                innerString=innerString.replace('{{{'+variableName+'.'+key+'}}}',list[j][key]);
              }
              if(innerString.includes('{{{}}}'))
              {
                innerString=innerString.replace('{{{}}}',j+1);
                console.log(innerString);
              }
             }         
             childs[k].innerHTML=innerString;
*/
//Method 2
             var innerString=childs[k].innerHTML;
             var len=innerString.length;
             //var equation=innerString.slice(3,len-3).replace(variableName,'employee');
             var equation=innerString.substring(3,len-3).replace(variableName,'employee');
             if(equation=="")
             {
               equation+=j+1;
             }
             childs[k].innerHTML=innerString.replace(innerString,eval(equation));

           }
           documentFragment.appendChild(componentClone);
         }
         parent.replaceChild(documentFragment,component);


         tableRowComNode.lastValueOfProperty=tableRowComNode.object[tableRowComNode.property];
       }
    }
  }
}

$$$.setControlModel=function(obj)
{
  var bind=[];
  var properties=Object.keys(obj);
  var list=document.querySelectorAll("[tm-if]");
  var i=0;
  var k=0;
  while(i<list.length)
  {
      var component=list[i];
      var bindValue=component.getAttribute("tm-if");
      console.log(bindValue);
      if(bindValue==null)return;
      var values=bindValue.split("=");

      var variableName=values[0];
      if(variableName.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;
      var modeValue=values[2];
      if(modeValue.length==0)throw "missing loop component in tag "+component.localName+" :: "+bindValue;

      var flag=false;
      for(const key of properties)
      {
        if(key==variableName)
           {
	      bind.push({property:key,component:list[i],object:obj});
              flag=true;
           }
       }
       if(!flag)throw "property doesn't exists in component in tag "+component.localName+" :: "+bindValue;
    i++;
  }
  setInterval(function(){checkControlModel(bind)},500);
}
function checkControlModel(bind)
{
  for(var x=0;x<bind.length;x++)
  {
    var component=bind[x].component;
    var bindValue=component.getAttribute("tm-if");
    if(bindValue==null)return;
    var values=bindValue.split("=");

    var variableName=values[0];
    var value=values[2];
    var mode=bind[x].object[bind[x].property];
    bindValue=bindValue.replace(variableName,"mode");
    if(eval(bindValue))
    {
      component.style.display='block';
    }
    else
    {
      component.style.display='none';
    }
  }
}





