@extends('layouts.admin')
@section('content_admin')
    <!-- Content -->
    <div class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
            <a href="{{ route('employee.verif') }}">
                <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                    <div class="flex justify-between mb-6">
                        <div>
                            <div class="flex items-center mb-1">
                                <div class="text-2xl font-semibold">{{ $countEmployeefalse }}</div>
                            </div>
                            <div class="text-sm font-medium text-gray-400">Employee Not Active</div>
                        </div>
                    </div>
                </div>
            </a>

            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-4">
                    <div>
                        <div class="flex items-center mb-1">
                            <div class="text-2xl font-semibold">{{ $countAdmin }}</div>
                        </div>
                        <div class="text-sm font-medium text-gray-400">Admin Count</div>
                    </div>
                </div>
            </div>
            <a href="{{ route('admin.employee.management') }}">
                <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                    <div class="flex justify-between mb-6">
                        <div>
                            <div class="text-2xl font-semibold mb-1">{{ $countEmployee }}</div>
                            <div class="text-sm font-medium text-gray-400">Employee Count</div>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
@endsection
