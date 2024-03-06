<?php

namespace App\Http\Controllers;

use App\Models\Borrowers;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller;

class AdminIndexController extends Controller
{
    public function index(){
        $countEmployeefalse=User::where('active','false')->count();
        $countAdmin=User::where('active','true')->where('role','admin')->count();
        $countEmployee=User::where('active','true')->where('role','employee')->count();
        return view('pages.admin.index',['countEmployeefalse'=>$countEmployeefalse,'countAdmin'=>$countAdmin,'countEmployee'=>$countEmployee]);
    }
}
