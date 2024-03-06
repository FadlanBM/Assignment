@extends('layouts.employees')
@section('content_admin')
    <!-- Content -->
    <div class="p-6">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-6">
                    <div>
                        <div class="flex items-center mb-1">
                            <div class="text-2xl font-semibold">{{$countBorrowers}}</div>
                        </div>
                        <div class="text-sm font-medium text-gray-400">Borrowers Count</div>
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-4">
                    <div>
                        <div class="flex items-center mb-1">
                            <div class="text-2xl font-semibold">{{$bookCount}}</div>
                        </div>
                        <div class="text-sm font-medium text-gray-400">Book Count</div>
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-6">
                    <div>
                        <div class="text-2xl font-semibold mb-1">{{$categoryCount}}</div>
                        <div class="text-sm font-medium text-gray-400">Category Count</div>
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-6">
                    <div>
                        <div class="text-2xl font-semibold mb-1">{{$lendingsTrue}}</div>
                        <div class="text-sm font-medium text-gray-400">Loan amount Active</div>
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-6">
                    <div>
                        <div class="text-2xl font-semibold mb-1">{{$lendingsFalse}}</div>
                        <div class="text-sm font-medium text-gray-400">The loan amount is not active</div>
                    </div>
                </div>
            </div>
            <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
                <div class="flex justify-between mb-6">
                    <div>
                        <div class="text-2xl font-semibold mb-1">{{$lendingsReturn}}</div>
                        <div class="text-sm font-medium text-gray-400">The loan amount has been returned</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
